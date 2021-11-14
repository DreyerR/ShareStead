import React, { useState } from 'react';
import { Card, Col, Row, Form, Button } from 'react-bootstrap';
import { Navigate } from 'react-router-dom';

import { faSignInAlt, faRedo } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import CustomToast from './CustomToast';
import axios from 'axios';

export default function Login() {

    const [loginDetails, setLoginDetails] = useState({ email: "", password: "" });
    const [didLogin, updateLogin] = useState(false);
    const [didSubmitForm, updateSubmitForm] = useState(false);
    const [showToast, updateShowToast] = useState(false);
    const [isSuccessful, setIsSuccessful] = useState(true);
    const [toastMessage, setToastMessage] = useState("");

    const handleInputChange = e => {
        setLoginDetails(
            {
                ...loginDetails,
                [e.target.name]: e.target.value
            }
        );
    }

    const handleSubmit = e => {
        e.preventDefault();

        updateSubmitForm(true);
        handleLoginRequest();
    }

    const handleLoginRequest = () => {

        const headers = {
            'Content-Type': 'application/json',
        }

        axios.post("http://localhost:8060/member/login", loginDetails, {
            headers: headers
        }).then(response => {
            if (response.status === 200) {
                handlePostRegister(true, response.data.message);
            }
            else {
                handlePostRegister(false, response.data.message);
            }
        }).catch(error => {
            handlePostRegister(false, "Username or password is incorrect");
        });
    }

    const handlePostRegister = (wasSuccessful, responseMessage) => {
        cancelForm(); // Clears the form

        setIsSuccessful(wasSuccessful);
        setToastMessage(responseMessage);
        updateShowToast(true);

        setTimeout(() => {
            if (wasSuccessful) {
                updateLogin(!didLogin);
            }
            else {
                updateShowToast(false);
                updateSubmitForm(false);
            }
        }, 2000);
    }

    const cancelForm = () => {
        document.getElementById("loginFormId").reset();
    }

    if (didLogin) {
        return <Navigate to="/welcome" />
    }

    return (
        <div>
            <div style={{ "display": showToast ? "block" : "none" }}>
                <CustomToast success={isSuccessful} message={toastMessage} />
            </div>
            <Card>
                <Card.Header style={{ fontSize: '2rem', fontWeight: 'bold' }}>
                    <div>
                        <FontAwesomeIcon icon={faSignInAlt} style={{ color: '#e2821d' }} />  Login
                    </div>
                </Card.Header>

                <Card.Title className="mb-1 p-3">Login with your email and password:</Card.Title>
                <Form onSubmit={handleSubmit} id="loginFormId">
                    <Card.Body>
                        <Row>
                            <Col lg={12}>
                                <Form.Group className="mb-3" controlId="formBasicEmail">
                                    <Form.Label>Email:</Form.Label>
                                    <Form.Control type="email" placeholder="name@example.com" name="email"
                                        onChange={handleInputChange} required />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col lg={12}>
                                <Form.Group className="mb-3" controlId="formBasicPassword">
                                    <Form.Label>Password:</Form.Label>
                                    <Form.Control type="password" placeholder="Enter your password" name="password"
                                        onChange={handleInputChange} required />
                                </Form.Group>
                            </Col>
                        </Row>
                    </Card.Body>
                    <Card.Footer>
                        <div className="d-flex justify-content-end">
                            <Button variant="primary" onClick={cancelForm}>
                                <FontAwesomeIcon icon={faRedo} style={{ marginRight: '5px' }} /> Clear
                            </Button>

                            <Button type="submit" className="ms-3" variant="success" disabled={didSubmitForm}>
                                <div><FontAwesomeIcon icon={faSignInAlt} style={{ marginRight: '5px' }} />
                                    {didSubmitForm ? 'Please wait...' : 'Login'}
                                </div>
                            </Button>
                        </div>
                    </Card.Footer>
                </Form>
            </Card>
        </div>
    )
}