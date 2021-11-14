import React from 'react'
import { Navbar, Nav, Container } from 'react-bootstrap';
import {Link} from 'react-router-dom';

const Navigationbar = () => {
    return (
        <Navbar style={{ backgroundColor: "#e2821d" }} variant="dark" expand="md">
            <Container>
                <Link to={""} className="navbar-brand">
                    ShareStead
                </Link>

                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Item>
                            <Link to={"welcome"} className="nav-link">Welcome</Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Link to={"gallery"} className="nav-link">Your Gallery</Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Link to={"upload"} className="nav-link">Upload Photos</Link>
                        </Nav.Item>
                    </Nav>
                    <Nav className="ms-auto">
                        <Nav.Item>
                            <Link to={"login"} className="nav-link">Login</Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Link to={"register"} className="nav-link">Register</Link>
                        </Nav.Item>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default Navigationbar
