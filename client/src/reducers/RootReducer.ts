import { combineReducers } from "redux";
import EventReducer from "./EventReducer";
import serviceReducer from "./ServiceReducer";

const RootReducer = combineReducers({
    services: serviceReducer,
    events: EventReducer
})

export default RootReducer