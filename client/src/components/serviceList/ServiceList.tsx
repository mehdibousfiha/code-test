import React from 'react';
import { Badge, Col, ListGroup, Row } from 'react-bootstrap';
import { PencilSquare, Trash } from 'react-bootstrap-icons';
import Alert from 'react-bootstrap/Alert'
import { connect } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { DispatchUpdate } from '../../actions/EventActions';
import { GetServices, Service, DeleteService } from '../../actions/ServiceActions';
import { ServiceActionstypes } from '../../actions/types';
import { RootStore } from '../../store';
import './ServiceList.css';


type PropsType = ReturnType<typeof mapStateToProps> & ReturnType<typeof mapDispatchToProps>

class ServiceList extends React.Component<PropsType> {
    state = { count: 0 };

    componentDidMount = () => {
        this.props.fetchServices()
    }

    delete = (id: number | undefined) => {
        if (id) {
            this.props.deleteService(id)
            this.props.fetchServices()
        }
    }

    edit = (service: Service) => {
        this.props.dispatchUpdate(service)
    }

    render() {
        return (
            <ListGroup>
                {this.props.services.length === 0
                    ? <div style={{ marginTop: '5%' }}>
                        <Alert variant="light">
                            <Row>
                                <Col md={{ span: 4, offset: 4 }}>Nothing to show</Col>
                            </Row>
                        </Alert>
                    </div>
                    : (this.props.services.map(service => (
                        <ListGroup.Item variant="light" key={service.id}>
                            <Row>
                                <Col sm={4}>
                                    <small>name : </small>
                                    {service.name}
                                </Col>
                                <Col sm={4}>
                                    <small>url : </small>
                                    {service.url}
                                </Col>
                                <Col sm={2}>
                                    <small>status : </small>
                                    <Badge variant={service.status === "OK" ? "success" : "danger"}>
                                        {service.status}
                                    </Badge>
                                </Col>
                                <Col sm={2}>
                                    <Badge variant="light"
                                        className="float-right"
                                        style={{ cursor: 'pointer' }}
                                        onClick={() => this.edit(service)}
                                    ><PencilSquare size={15} /></Badge>
                                    <Badge variant="light"
                                        className="float-right"
                                        style={{ cursor: 'pointer' }}
                                        onClick={() => this.delete(service.id)}
                                    ><Trash size={15} /></Badge>
                                </Col>
                            </Row>
                        </ListGroup.Item>)
                    ))
                }
            </ListGroup>
        );
    }
}

const mapStateToProps = (state: RootStore) => ({
    services: state.services.response,
    loading: state.services.loading
});

const mapDispatchToProps = (dispatch: ThunkDispatch<any, any, ServiceActionstypes>) => ({
    fetchServices: () => dispatch(GetServices()),
    deleteService: (id: number) => dispatch(DeleteService(id)),
    dispatchUpdate: (service: Service) => dispatch(DispatchUpdate(service))
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ServiceList)

