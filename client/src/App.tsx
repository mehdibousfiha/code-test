import React from 'react';
import { connect } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { GetServices, Service } from './actions/ServiceActions';
import { ServiceActionstypes } from './actions/types';
import { DispatchUpdate } from './actions/EventActions';
import './App.css';
import ServiceAdd from './components/addService/ServiceAdd';
import ServiceList from './components/serviceList/ServiceList';
import { RootStore } from './store';

type PropsType = ReturnType<typeof mapStateToProps> & ReturnType<typeof mapDispatchToProps>

class App extends React.Component<PropsType, { service: Service | undefined }> {

  state = {
    service: undefined
  }

  componentDidMount = () => {
    setInterval(() => {
      this.props.fetchServices()
    }, 5000);
  }

  render = () => {
    return (
      <div className="container">
        <ServiceAdd />
        <ServiceList />
      </div>
    );
  }
}


const mapStateToProps = (state: RootStore) => ({
  service: state.services.response
});

const mapDispatchToProps = (dispatch: ThunkDispatch<any, any, ServiceActionstypes>) => ({
  fetchServices: () => dispatch(GetServices()),
  dispatchUpdate: (service: Service) => dispatch(DispatchUpdate((service))),
})

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App)

