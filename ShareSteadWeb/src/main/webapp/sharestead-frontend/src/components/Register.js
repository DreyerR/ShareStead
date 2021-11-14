import React, { useState } from 'react'
import { Navigate } from 'react-router-dom';
import axios from 'axios';

import { Card, Col, Form, Row, Button } from 'react-bootstrap';
import { faSave, faRedo } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import CustomToast from './CustomToast';

const Register = () => {

    const [details, setDetails] = useState({ firstName: "", lastName: "", email: "", password: "" });
    const [didRegister, updateRegister] = useState(false);
    const [didSubmitForm, updateSubmitForm] = useState(false);
    const [showToast, updateShowToast] = useState(false);
    const [isSuccessful, setIsSuccessful] = useState(true);
    const [toastMessage, setToastMessage] = useState("");

    const handlePostRequest = () => {

        const headers = {
            'Content-Type': 'application/json',
        }

        axios.post("http://localhost:8060/member/register", details, {
            headers: headers
        }).then(response => {
            if (response.status === 201) {
                handlePostRegister(true, response.data.message)
            }
            else {
                handlePostRegister(false, response.data.message);
            }
        }).catch(error => {
            console.log(error.message);
            handlePostRegister(false, "An unknown error has occurred");
        });
    }

    const handlePostRegister = (wasSuccessful, responseMessage) => {
        cancelForm(); // Clears the form

        setIsSuccessful(wasSuccessful);
        setToastMessage(responseMessage);
        updateShowToast(true);

        setTimeout(() => {
            if (wasSuccessful) {
                updateRegister(!didRegister);
            } 
            else {
                updateShowToast(false);
                updateSubmitForm(false);
            }
        }, 2000);
    }

    const handleInputChange = e => {
        setDetails(
            {
                ...details,
                [e.target.name]: e.target.value
            }
        );
    }

    const handleSubmit = e => {
        e.preventDefault();

        updateSubmitForm(true);
        handlePostRequest();
    }

    const cancelForm = () => {
        document.getElementById("registerFormId").reset();
    }

    if (didRegister) {
        return <Navigate to="/login" />
    }

    return (
        <div>
            <div style={{ "display": showToast ? "block" : "none" }}>
                <CustomToast success={isSuccessful} message={toastMessage} />
            </div>
            < Card className="border" >
                <Card.Header style={{ fontSize: '2rem', fontWeight: 'bold' }}>
                    <FontAwesomeIcon icon={faSave} style={{ color: '#e2821d' }} />  Registration
                </Card.Header>

                <Card.Title className="mb-1 p-3">Please provide us with the following information:</Card.Title>
                <Form onSubmit={handleSubmit} id="registerFormId">
                    <Card.Body>
                        <Row>
                            <Col lg={6} md={6}>
                                <Form.Group className="mb-3" controlId="formBasicFirstName">
                                    <Form.Label>First name:</Form.Label>
                                    <Form.Control type="text" placeholder="Enter first name" name="firstName"
                                        onChange={handleInputChange} values={details.firstName} required />
                                </Form.Group>
                            </Col>
                            <Col lg={6} md={6}>
                                <Form.Group className="mb-3" controlId="formBasicLastName">
                                    <Form.Label>Last name:</Form.Label>
                                    <Form.Control type="text" placeholder="Enter last name" name="lastName"
                                        onChange={handleInputChange} values={details.lastName} required />      {/* onChange={e => setDetails({lastName: e.target.value})} */}
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row>
                            <Col lg={6} md={6}>
                                <Form.Group className="mb-3" controlId="formBasicEmail">
                                    <Form.Label>Email address:</Form.Label>
                                    <Form.Control type="email" placeholder="name@example.com" name="email"
                                        onChange={handleInputChange} values={details.email} required />
                                </Form.Group>
                            </Col>
                            <Col lg={6} md={6}>
                                <Form.Group className="mb-3" controlId="formBasicPassword">
                                    <Form.Label>Password:</Form.Label>
                                    <Form.Control type="password" placeholder="Enter password" name="password"
                                        onChange={handleInputChange} values={details.password} required />
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
                                <div><FontAwesomeIcon icon={faSave} style={{ marginRight: '5px' }} />
                                    {didSubmitForm ? 'Please wait...' : 'Register'}
                                </div>
                            </Button>
                        </div>
                    </Card.Footer>
                </Form>
            </Card >
        </div>
    )
}

export default Register
