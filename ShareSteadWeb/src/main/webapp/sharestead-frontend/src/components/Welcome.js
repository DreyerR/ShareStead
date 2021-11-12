import React from 'react';
import { Col, Container, Row } from 'react-bootstrap';

const Welcome = () => {
    return (
        <Container>
            <Row>
                <Col lg={12}>
                    <div className="bg-light p-5 rounded-lg m-3">
                        <h1 className="display-7">Hi there!</h1>
                        <p className="lead">
                            To get started, please create an account or simply login.
                        </p>
                        <p>This platform enable the uploading, managing and sharing of images.</p>
                        <p>This is a project that was built for our CMPG323 module.</p>
                    </div>
                </Col>
            </Row>
        </Container>
    )
}

export default Welcome
