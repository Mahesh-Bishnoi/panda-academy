import './App.css';
import pandaLogo from '/panda.png'
import ScheduleView from './ScheduleView';

function App() {
    return (
        <div className="App">
            <header>
                <img src={pandaLogo} className="logo" alt="panda logo" /><h1>Panda Academy</h1>
            </header>
            <main>
                <ScheduleView />
            </main>
        </div>
    );
}

export default App;
