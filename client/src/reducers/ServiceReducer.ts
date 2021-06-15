import * as Types from '../actions/types'
import { Service } from '../actions/ServiceActions'

interface ServicesState {
    loading: boolean,
    response: Service[]
}

const defaultState: ServicesState = {
    loading: false,
    response: []
}

const servicesReducer = (state: ServicesState = defaultState, action: Types.ServiceActionstypes): ServicesState => {

    switch (action.type) {

        case Types.SERVICES_FAIL || Types.SERVICE_POST_FAIL:
            return {
                ...state,
                loading: false
            }
        case Types.SERVICES_LOADING || Types.SERVICE_POST_LOADING:
            return {
                ...state,
                loading: true
            }
        case Types.SERVICES_SUCCESS || Types.SERVICE_POST_SUCCESS:
            return {
                ...state,
                loading: false,
                response: action.payload
            }
        default:
            return state
    }
}

export default servicesReducer