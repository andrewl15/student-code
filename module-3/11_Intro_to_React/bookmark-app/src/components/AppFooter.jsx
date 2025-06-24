export default function AppFooter() {

  const currentYear = new Date().getFullYear();

  return (
    <footer id="app-footer">
      <div>&copy; {currentYear} Example, Inc.</div>
      <div>
        <a href="/about.html">About</a>
        &nbsp;|&nbsp;
        <a href="mailto:support@example.com">Contact Us</a>
      </div>
    </footer>
  );
}
