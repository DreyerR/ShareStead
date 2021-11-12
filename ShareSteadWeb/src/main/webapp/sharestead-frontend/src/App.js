import { Col, Container, Row } from 'react-bootstrap';
import './App.css';
import NavigationBar from './components/NavigationBar';

function App() {

  const marginTop = {
    marginTop: "2%"
  }

  return (
    <div>
      <NavigationBar />
      <Container>
        <Row>
          <Col lg={12} style={marginTop}>
            <div class="bg-light p-5 rounded-lg m-3">
              <h1 class="display-7">Hi there!</h1>
              <p class="lead">
                To get started, please create an account or simply login.
              </p>
              <p>This platform enable the uploading, managing and sharing of images.</p>
              <p>This is a project that was built for our CMPG323 module.</p>
            </div>
          </Col>
        </Row>
      </Container>

    </div>
  );
}

export default App;
