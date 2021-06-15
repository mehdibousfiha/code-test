import { createStore, applyMiddleware } from "redux";
import RootReducer from "./reducers/RootReducer";
import thunk, { ThunkMiddleware } from 'redux-thunk'
import { AppActions } from "./actions";

const strore = createStore(
    RootReducer,
    applyMiddleware(
        thunk as ThunkMiddleware<RootStore, AppActions>
    )
)

export type RootStore = ReturnType<typeof RootReducer>
export default strore