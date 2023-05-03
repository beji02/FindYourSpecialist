import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          Welcome to Find Your Specialist page!
        </p>
        {/*insert a link to login page*/}
        <a
            className="App-link"
            href="/login"
            target="_blank"
            rel="noopener noreferrer"
        >
            Login
        </a>
        {/*insert a link to signup page*/}
        <a
            className="App-link"
            href="/signup"
            target="_blank"
            rel="noopener noreferrer"
        >
            Signup
        </a>
      </header>
    </div>
  );
}

export default App;
