import React, {useEffect, useRef, useState} from 'react';
import {fetchNdjson, fetchNdjsonWithState} from "../api/ApiFunctions";

const MainPage = () => {

    const [stockSettings, setStockSettings] = useState({stock: [], userSettings: []});
    const [info, setInfo] = useState({info: []});
    const [token, setToken] = useState(localStorage.getItem("jwtToken") || "");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const infoAbortController = useRef(null);

    useEffect(() => {
        async function fetchData() {
            fetchNdjson('/api/stockSettings', token)
                .then(data => setStockSettings(data));
        }

        async function fetchInfo() {
            infoAbortController.current = new AbortController();
            fetchNdjsonWithState('/api/info', (item) => setInfo(item), token, infoAbortController.current.signal)
                .then(info => setInfo(info));
        }
        console.log("текущий ", localStorage.getItem("jwtToken"))
        if (token !== "") {
            fetchData().then();
            fetchInfo().then();
        }
    }, [token]);

    const handleInputChange = (e, ticket) => {
        const newSettings = {...stockSettings};
        const setting = newSettings.userSettings.find(s => s.ticket === ticket);
        if (setting) {
            setting.weight = e.target.value;
        } else {
            newSettings.userSettings.push({ticket, weight: e.target.value});
        }
        setStockSettings(newSettings);
    }

    const handleSave = () => {
        fetch('/api/settings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(stockSettings.userSettings)
        })
            .then(response => response.json())
            .then(data => console.log('Success:', data))
            .catch((error) => {
                console.error('Error:', error);
            });
    }

    const handleExit = () => {
        if (infoAbortController.current) {
            infoAbortController.current.abort();
        }
        setStockSettings({stock: [], userSettings: []});
        setInfo({info: []});
        setUsername('')
        setPassword('')
        setToken('')
        localStorage.removeItem("jwtToken")
    }

    const login = () => {
        fetch('http://localhost:8083/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({username, password})
        }).then(response => response.json())
            .then(data => {
                console.log('Success:', data.token)
                localStorage.setItem("jwtToken", data.token)
                setToken(data.token);
            })
    }

    const totalWeight = stockSettings.stock.reduce((sum, ind) => {
        const setting = stockSettings.userSettings.find(s => s.ticket === ind.ticket);
        const product = ind.weight * (setting ? setting.weight : 1);
        return sum + product;
    }, 0);

    return (
        <div>
            { token === "" ? (
                <>
                    <h2>Login</h2>
                    <input
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    /><br/>
                    <input
                        placeholder="Password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    /><br/>
                    <button onClick={login}>Login</button>
                </>
            ) : (
                <>
                    <div>
                        <table>
                            <thead>
                            <tr>
                                <th>№</th>
                                <th>Ticket</th>
                                <th>Name</th>
                                <th>MOEX Weight</th>
                                <th>Modifier</th>
                                <th>Weight with modifier</th>
                                <th>My Index</th>
                            </tr>
                            </thead>
                            <tbody>
                            {stockSettings.stock.map((ind, i) => {
                                const setting = stockSettings.userSettings.find(s => s.ticket === ind.ticket);
                                const product = ind.weight * (setting ? setting.weight : 1);
                                const formattedProduct = parseFloat(product).toFixed(3).replace('.', ',');
                                const myIndex = product / totalWeight * 100;
                                const formattedMyIndex = parseFloat(myIndex).toFixed(3).replace('.', ',');
                                return (<tr key={i}>
                                    <td>{i + 1}</td>
                                    <td>{ind.ticket}</td>
                                    <td>{ind.name}</td>
                                    <td>{ind.weight}</td>
                                    <td>
                                        <input type="number" value={setting ? setting.weight : 1}
                                               onChange={(e) => handleInputChange(e, ind.ticket)}/>
                                    </td>
                                    <td>{formattedProduct}</td>
                                    <td>{formattedMyIndex}</td>
                                </tr>);
                            })}
                            </tbody>
                        </table>
                    </div>
                    <div className="form-inline">
                        <a onClick={handleSave}>Save</a>
                        <a onClick={handleExit}>Exit</a>
                    </div>
                    <footer>
                        <p>{info.info}</p>
                    </footer>
                </>)}
        </div>
    )
}

export default MainPage;