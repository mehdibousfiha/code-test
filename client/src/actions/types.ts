import { Service } from "./ServiceActions"

export const SERVICES_LOADING = "SERVICES_LOADING"
export const SERVICES_FAIL = "SERVICES_FAIL"
export const SERVICES_SUCCESS = "SERVICES_SUCCESS"

interface ServicesLoading {
    type: typeof SERVICES_LOADING
}

interface ServicesFail {
    type: typeof SERVICES_FAIL
}

interface ServicesSuccess {
    type: typeof SERVICES_SUCCESS,
    payload: Service[]
}


export const SERVICE_POST_LOADING = "SERVICE_POST_LOADING"
export const SERVICE_POST_FAIL = "SERVICE_POST_FAIL"
export const SERVICE_POST_SUCCESS = "SERVICE_POST_SUCCESS"

interface ServicePostLoading {
    type: typeof SERVICE_POST_LOADING
}

interface ServicePostFail {
    type: typeof SERVICE_POST_FAIL
}

interface ServicePostSuccess {
    type: typeof SERVICE_POST_SUCCESS,
    payload: string
}


export const DISPATCH_UPDATE = "DISPATCH_UPDATE"

interface DispatchUpdate {
    type: typeof DISPATCH_UPDATE,
    payload: Service | undefined
}


export type ServiceActionstypes =
    ServicesLoading | ServicesSuccess | ServicesFail |
    ServicePostLoading | ServicePostSuccess | ServicePostFail |
    DispatchUpdate