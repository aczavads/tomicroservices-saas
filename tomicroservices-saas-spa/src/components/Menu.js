import { Nav, Container } from 'react-bootstrap'
import { LinkContainer } from 'react-router-bootstrap'

const Menu = () => {
  return (
    <Container>
      <header className='d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom'>
        <LinkContainer to='/'>
          <Nav.Link className='d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none'>
            toMicroservices SAAS
          </Nav.Link>
        </LinkContainer>
        <Nav>
          <ul className='nav col-12 col-md-auto mb-2 justify-content-center mb-md-0'>
            <li>
              <LinkContainer to='/about'>
                <Nav.Link className='nav-link px-2 link-secondary'>About</Nav.Link>
              </LinkContainer>
            </li>
            <li>
              <LinkContainer to='/executar-experimento'>
                <Nav.Link className='nav-link px-2 link-secondary'>Executar Experimento</Nav.Link>
              </LinkContainer>
            </li>
            {/* <li>
              <LinkContainer to='/login'>
                <Nav.Link className='nav-link px-2 link-secondary'>Login</Nav.Link>
              </LinkContainer>
            </li> */}
          </ul>
        </Nav>
      </header>
    </Container>
  )
}

export default Menu
