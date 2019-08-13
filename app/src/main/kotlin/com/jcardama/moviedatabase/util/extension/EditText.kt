@file:Suppress("PrivatePropertyName", "unused")

package com.jcardama.moviedatabase.util.extension

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun <T> EditText.changeListener(listener: (field: EditText?, s: String) -> T?) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.invoke(this@changeListener, s.toString())
        }
    })
}

/**
 * Add validations to EditText
 */
fun EditText.validator(): Validator<EditText> =
        Validator(text.toString(), this)

/**
 * Add validations to TextInputEditText
 */
fun TextInputEditText.validator(): Validator<TextInputEditText> =
        Validator(text.toString(), this)

/**
 * Add validations to TextInputLayout
 */
fun TextInputLayout.validator(): Validator<TextInputLayout> =
        Validator(editText?.text.toString(), this)

/*
* Class to process all the filters provided by the user
* */
class Validator<T>(private val text: String, val view: T?) {

    /*
    * Boolean to determine whether all the validations has passed successfully.
    * If any validation fails the state is changed to false.
    * Final result is returned to the user
    * */
    private var isValidated = true

    /*
    * If validation fails then this callback is invoked to notify the user about
    * and error
    * */
    private var errorCallback: ((error: ValidationError?, field: T?) -> Unit)? = null

    /*
    * If validation is passes then this callback is invoked to notify the user
    * for the same
    * */
    private var successCallback: ((field: T?) -> Unit)? = null

    /*
    * Movie settable limits for the numbers of characters that the string can contain
    * */
    private var MINIMUM_LENGTH = 0
    private var MAXIMUM_LENGTH = Int.MAX_VALUE

    private var VALIDATION_ERROR_TYPE: ValidationError? = null

    fun validate(): Boolean {
        //Check if the string characters count is in limits
        if (text.length < MINIMUM_LENGTH) {
            isValidated = false
            setErrorType(ValidationError.MINIMUM_LENGTH)
        } else if (text.length > MAXIMUM_LENGTH) {
            isValidated = false
            setErrorType(ValidationError.MAXIMUM_LENGTH)
        }

        //Invoke the error callback if supplied by the user
        if (isValidated) {
            successCallback?.invoke(view)
        } else {
            errorCallback?.invoke(VALIDATION_ERROR_TYPE, view)
        }

        return isValidated
    }

    fun email(): Validator<T> {
        if (!text.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$"))) {
            setErrorType(ValidationError.EMAIL)
        }
        return this
    }

    fun noNumbers(): Validator<T> {
        if (text.matches(Regex(".*\\d.*"))) {
            setErrorType(ValidationError.NO_NUMBERS)
        }
        return this
    }

    fun nonEmpty(): Validator<T> {
        if (text.isEmpty()) {
            setErrorType(ValidationError.NON_EMPTY)
        }
        return this
    }

    fun onlyNumbers(): Validator<T> {
        if (!text.matches(Regex("\\d+"))) {
            setErrorType(ValidationError.ONLY_NUMBERS)
        }
        return this
    }

    @SuppressLint("DefaultLocale")
    fun allUpperCase(): Validator<T> {
        if (text.toUpperCase() != text) {
            setErrorType(ValidationError.ALL_UPPER_CASE)
        }
        return this
    }

    @SuppressLint("DefaultLocale")
    fun allLowerCase(): Validator<T> {
        if (text.toLowerCase() != text) {
            setErrorType(ValidationError.ALL_LOWER_CASE)
        }
        return this
    }

    fun atLeastOneLowerCase(): Validator<T> {
        if (text.matches(Regex("[A-Z0-9]+"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_LOWER_CASE)
        }
        return this
    }

    fun atLeastOneUpperCase(): Validator<T> {
        if (text.matches(Regex("[a-z0-9]+"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_UPPER_CASE)
        }
        return this
    }

    fun maximumLength(length: Int): Validator<T> {
        MAXIMUM_LENGTH = length
        return this
    }

    fun minimumLength(length: Int): Validator<T> {
        MINIMUM_LENGTH = length
        return this
    }

    fun addErrorCallback(callback: (error: ValidationError?, field: T?) -> Unit): Validator<T> {
        errorCallback = callback
        return this
    }

    fun addSuccessCallback(callback: (field: T?) -> Unit): Validator<T> {
        successCallback = callback
        return this
    }

    fun atLeastOneNumber(): Validator<T> {
        if (!text.matches(Regex(".*\\d.*"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_NUMBER)
        }
        return this
    }

    fun startsWithNonNumber(): Validator<T> {
        if (Character.isDigit(text[0])) {
            setErrorType(ValidationError.STARTS_WITH_NON_NUMBER)
        }
        return this
    }

    fun noSpecialCharacter(): Validator<T> {
        if (!text.matches(Regex("[A-Za-z0-9]+"))) {
            setErrorType(ValidationError.NO_SPECIAL_CHARACTER)
        }
        return this
    }

    fun atLeastOneSpecialCharacter(): Validator<T> {
        if (text.matches(Regex("[A-Za-z0-9]+"))) {
            setErrorType(ValidationError.AT_LEAST_ONE_SPECIAL_CHARACTER)
        }
        return this
    }

    fun contains(string: String): Validator<T> {
        if (!text.contains(string)) {
            setErrorType(ValidationError.CONTAINS)
        }
        return this
    }

    fun doesNotContains(string: String): Validator<T> {
        if (text.contains(string)) {
            setErrorType(ValidationError.DOES_NOT_CONTAINS)
        }
        return this
    }

    fun startsWith(string: String): Validator<T> {
        if (!text.startsWith(string)) {
            setErrorType(ValidationError.STARTS_WITH)
        }
        return this
    }

    fun endsWith(string: String): Validator<T> {
        if (!text.endsWith(string)) {
            setErrorType(ValidationError.ENDS_WITH)
        }
        return this
    }

    @Suppress("CovariantEquals")
    fun equals(string: String): Validator<T> {
        if (text != string) {
            setErrorType(ValidationError.EQUALS)
        }
        return this
    }

    fun creditCard(): Validator<T> {
        val ccNumber = text.replace(" ", "")
        var sum = 0
        var alternate = false
        for (i in ccNumber.length - 1 downTo 0) {
            var n = Integer.parseInt(ccNumber.substring(i, i + 1))
            if (alternate) {
                n *= 2
                if (n > 9) {
                    n = n % 10 + 1
                }
            }
            sum += n
            alternate = !alternate
        }
        if (sum % 10 != 0) {
            setErrorType(ValidationError.CREDIT_CARD_NUMBER)
        }
        return this
    }

    fun nit(): Validator<T> {
        if (!text.matches(Regex("^(?i)((\\d+)-(\\d|K)|CF|C/F)$"))) {
            setErrorType(ValidationError.NIT)
        }
        return this
    }

    private fun setErrorType(validationError: ValidationError) {
        isValidated = false
        if (VALIDATION_ERROR_TYPE == null) {
            VALIDATION_ERROR_TYPE = validationError
        }
    }
}

/*
* Enums that serve for identification of error while validation text.
* Every enum is the name of a function with the corresponding validation
* */
enum class ValidationError {
    MINIMUM_LENGTH, MAXIMUM_LENGTH, AT_LEAST_ONE_UPPER_CASE, AT_LEAST_ONE_LOWER_CASE, ALL_LOWER_CASE, ALL_UPPER_CASE, ONLY_NUMBERS, NON_EMPTY, NO_NUMBERS, EMAIL, AT_LEAST_ONE_NUMBER, STARTS_WITH_NON_NUMBER, NO_SPECIAL_CHARACTER, AT_LEAST_ONE_SPECIAL_CHARACTER, CONTAINS, DOES_NOT_CONTAINS, EQUALS, STARTS_WITH, ENDS_WITH, CREDIT_CARD_NUMBER, NIT
}
