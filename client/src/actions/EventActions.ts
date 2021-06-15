import { Dispatch } from "redux";
import * as Types from './types'
import { Service } from "./ServiceActions";


export const DispatchUpdate = (service: Service | undefined) => async (dispatch: Dispatch<Types.ServiceActionstypes>) => {
    dispatch({
        type: Types.DISPATCH_UPDATE,
        payload: service
    })
}
