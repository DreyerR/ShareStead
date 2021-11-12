import React from 'react'

import { Card, Col, Form, Row, Button } from 'react-bootstrap';
import { faSave } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

const Register = () => {
    return (
        <Card className="border">
            <Card.Header style={{ fontSize: '2rem', fontWeight: 'bold' }}>
                <FontAwesomeIcon icon={faSave} style={{color: '#e2821d'}} />  Registration
            </Card.Header>
            <Card.Body>
                <Card.Title className="mb-4">Please provide us with the following information:</Card.Title>
                <Form>
                    <Row>
                        <Col lg={6} md={6}>
                            <Form.Group className="mb-3" controlId="formBasicFirstName">
                                <Form.Label>First name:</Form.Label>
                                <Form.Control type="text" placeholder="Enter first name" />
                            </Form.Group>
                        </Col>
                        <Col lg={6} md={6}>
                            <Form.Group className="mb-3" controlId="formBasicLastName">
                                <Form.Label>Last name:</Form.Label>
                                <Form.Control type="text" placeholder="Enter last name" />
                            </Form.Group>
                        </Col>
                    </Row>

                    <Row>
                        <Col lg={6} md={6}>
                            <Form.Group className="mb-3" controlId="formBasicEmail">
                                <Form.Label>Email address:</Form.Label>
                                <Form.Control type="email" placeholder="name@example.com" />
                            </Form.Group>
                        </Col>
                        <Col lg={6} md={6}>
                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Password:</Form.Label>
                                <Form.Control type="password" placeholder="Enter password" />
                            </Form.Group>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
            <Card.Footer>
                <div className="d-flex justify-content-end">
                    <Button style={{ width: '130px' }} variant="primary">Clear</Button>
                    <Button style={{ width: '130px' }} className="ms-3" variant="success">Register</Button>
                </div>
            </Card.Footer>
        </Card>
    )
}

export default Register
