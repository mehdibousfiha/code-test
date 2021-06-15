import { Dispatch } from "redux";
import axios from "axios";
import * as Types from './types'


export type Service = {
    id?: number
    name?: string
    url?: string
    status?: string
}

export const GetServices = () => async (dispatch: Dispatch<Types.ServiceActionstypes>) => {

    try {
        dispatch({
            type: Types.SERVICES_LOADING
        })

        const resp = await axios.get('/api/service')

        dispatch({
            type: Types.SERVICES_SUCCESS,
            payload: resp.data
        })

    } catch (e) {
        dispatch({
            type: Types.SERVICES_FAIL
        })
    }

}

export const PostService = (service: Service) => async (dispatch: Dispatch<Types.ServiceActionstypes>) => {

    try {
        dispatch({
            type: Types.SERVICE_POST_LOADING
        })

        const resp = await axios.post('/api/service', service)

        dispatch({
            type: Types.SERVICE_POST_SUCCESS,
            payload: resp.data
        })

    } catch (e) {
        dispatch({
            type: Types.SERVICE_POST_FAIL
        })
    }

}


export const UpdateService = (id: number, service: Service) => async (dispatch: Dispatch<Types.ServiceActionstypes>) => {

    try {
        dispatch({
            type: Types.SERVICE_POST_LOADING
        })

        const resp = await axios.put(`/api/service/${id}`, service)

        dispatch({
            type: Types.SERVICE_POST_SUCCESS,
            payload: resp.data
        })

    } catch (e) {
        dispatch({
            type: Types.SERVICE_POST_FAIL
        })
    }

}

export const DeleteService = (id: number) => async (dispatch: Dispatch<Types.ServiceActionstypes>) => {

    try {
        dispatch({
            type: Types.SERVICE_POST_LOADING
        })

        const resp = await axios.delete(`/api/service/${id}`)

        dispatch({
            type: Types.SERVICE_POST_SUCCESS,
            payload: resp.data
        })

    } catch (e) {
        dispatch({
            type: Types.SERVICE_POST_FAIL
        })
    }

}