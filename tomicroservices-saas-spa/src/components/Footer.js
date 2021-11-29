const Footer = () => {
  const year = new Date().getFullYear()
  return (
    <>
      <footer className='text-center text-capitalize'>
        copyright Otimizes (UEM) &copy; {year}
      </footer>
    </>
  )
}

export default Footer
