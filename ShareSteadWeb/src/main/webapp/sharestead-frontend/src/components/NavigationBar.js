import React from 'react'
import { Navbar, Nav, Container } from 'react-bootstrap';

const Navigationbar = () => {
    return (
        <Navbar style={{ backgroundColor: "#e2821d" }} variant="dark" expand="md">
            <Container>
                <Navbar.Brand href="#welcome">ShareStead</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Item>
                            <Nav.Link href="#welcome">Welcome</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="#gallery">Your Gallery</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="#share">Share Photos</Nav.Link>
                        </Nav.Item>
                    </Nav>
                    <Nav className="ms-auto">
                        <Nav.Item>
                            <Nav.Link href="#login">Login</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link href="#register">Register</Nav.Link>
                        </Nav.Item>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Navigationbar
