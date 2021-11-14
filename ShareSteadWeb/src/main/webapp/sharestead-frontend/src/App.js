import './App.css';
import { Col, Container, Row } from 'react-bootstrap';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import NavigationBar from './components/NavigationBar';
import Welcome from './components/Welcome';
import Photo from './components/Photo';
import Gallery from './components/Gallery';
import Register from './components/Register';
import Login from './components/Login';

function App() {

  const marginTop = {
    marginTop: "2%"
  }

  return (
    <Router>
      <NavigationBar />

      <Container>
        <Row>
          <Col lg={12} style={marginTop}>
            <Routes>
              <Route path="/" exact element={<Welcome />} />
              <Route path="/welcome" exact element={<Welcome />} />
              <Route path="/upload" exact element={<Photo />} />
              <Route path="/gallery" exact element={<Gallery />} />
              <Route path="/register" exact element={<Register />} />
              <Route path="/login" exact element={<Login />} />
            </Routes>
          </Col>
        </Row>
      </Container>

    </Router>
  );
}

export default App;
