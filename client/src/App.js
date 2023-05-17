import './style/App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          Welcome to Find Your Specialist page!
        </p>
        <a
            className="App-link"
            href="/login"
            rel="noopener noreferrer"
        >
            Login
        </a>
        <a
            className="App-link"
            href="/register"
            rel="noopener noreferrer"
        >
            Register
        </a>
          <a
              className="App-link"
              href="/home"
              rel="noopener noreferrer"
          >
              Home
          </a>
      </header>
    </div>
  );
}

export default App;
