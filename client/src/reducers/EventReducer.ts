import * as Types from '../actions/types'
import { Service } from '../actions/ServiceActions'

interface EventState {
    response: Service | undefined
}

const defaultState: EventState = {
    response: undefined
}

const EventReducer = (state: EventState = defaultState, action: Types.ServiceActionstypes): EventState => {
    switch (action.type) {
        case Types.DISPATCH_UPDATE:
            return { response: action.payload }
        default:
            return state
    }
}

export default EventReducer