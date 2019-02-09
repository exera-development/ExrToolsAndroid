package io.exera.exrtools

import io.exera.pushr.util.ExrState


/**
 * Created by Attila Janosi on 11/09/2018.
 * EXERA SOFTDEVELOP SRL
 * https://exera.io
 */

class ExrViewState<T>(var state: ExrState, var data: T?, var error: ExrError?) {
    constructor(data: T?) : this(ExrState.DONE, data, null)
    constructor(state: ExrState) : this(state, null, null)
    constructor(error: ExrError) : this(ExrState.ERROR, null, error)
}

class ExrError(val message: String = "", val errorCode: Int = ExrErrorCodes.UNKNOWN)

