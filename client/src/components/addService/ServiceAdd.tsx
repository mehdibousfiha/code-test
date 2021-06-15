import React from 'react';
import { Button, Form, Col } from 'react-bootstrap';
import { connect } from 'react-redux';
import { ThunkDispatch } from 'redux-thunk';
import { DispatchUpdate } from '../../actions/EventActions';
import { PostService, GetServices, Service, UpdateService } from '../../actions/ServiceActions';
import { ServiceActionstypes } from '../../actions/types';
import { RootStore } from '../../store';
import './ServiceAdd.css';

type PropsType = ReturnType<typeof mapStateToProps> & ReturnType<typeof mapDispatchToProps>
type ServiceAddState = {
    service: Service | undefined
}

class ServiceAdd extends React.Component<PropsType, ServiceAddState> {

    state: ServiceAddState = {
        service: undefined
    }

    add = () => {
        if (this.state.service) {
            this.props.postService(this.state.service)
                .then(() => {
                    this.props
                        .dispatchUpdate(undefined)
                        .then(() => this.setState({ service: undefined }))
                    this.props.fetchServices()
                })
        }
    }

    update = () => {
        if (this.state.service && this.props.model?.id) {
            this.props.updateService(this.props.model.id, this.state.service)
                .then(() => {
                    this.props
                        .dispatchUpdate(undefined)
                        .then(() => this.setState({ service: undefined }))
                    this.props.fetchServices()
                })
        }
    }

    componentDidUpdate = (props: PropsType) => {
        if (props.model !== this.props.model) {
            this.setState({ service: this.props.model })
        }
    }

    url = (e: any) => {

        
        this.setState({ service: { ...this.state.service, url: e.target.value } })
    }

    render() {
        return (
            <div className="service-add">
                <Form>
                    <Form.Row>
                        <Col sm={4}>
                            <Form.Control
                                required={true}
                                placeholder="Service name"
                                value={this.state.service?.name ?? ''}
                                onChange={e => this.setState({ service: { ...this.state.service, name: e.target.value } })} />
                        </Col>
                        <Col sm={6}>
                            <Form.Control
                                required
                                placeholder="Service url"
                                value={this.state.service?.url ?? ''}
                                onChange={this.url} />
                        </Col>
                        <Col sm={2}>
                            <Button variant="outline-primary" block onClick={this.props.model ? this.update : this.add}>
                                {this.props.model ? 'UPDATE' : 'ADD'}
                            </Button>
                        </Col>
                    </Form.Row>
                </Form>
            </div>
        );
    }
}

const mapStateToProps = (state: RootStore) => ({
    model: state.events.response
});

const mapDispatchToProps = (dispatch: ThunkDispatch<any, any, ServiceActionstypes>) => ({
    dispatchUpdate: (service: Service | undefined) => dispatch(DispatchUpdate(service)),
    postService: (service: Service) => dispatch(PostService(service)),
    updateService: (id: number, service: Service) => dispatch(UpdateService(id, service)),
    fetchServices: () => dispatch(GetServices()),
})

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ServiceAdd)
