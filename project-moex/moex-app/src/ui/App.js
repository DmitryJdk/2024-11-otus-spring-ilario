import React from 'react'
import './style/site.module.css'
import {Route, Routes} from "react-router-dom";
import MainPage from "./components/MainPage";

export default class App extends React.Component {

    render() {
        return (
            <Routes>
                <Route path="/" element={<MainPage/>} />
            </Routes>
        )
    }
};