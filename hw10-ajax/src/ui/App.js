import React from 'react'
import './style/site.module.css'
import {Route, Routes} from "react-router-dom";
import Books from "./components/Books";
import Menu from "./components/Menu";
import Authors from "./components/Authors";
import Genres from "./components/Genres";
import HOCBook from "./components/Book";
import HOCNewBook from "./components/NewBook";
import HOCComments from "./components/Comments";

export default class App extends React.Component {

    render() {
        return (
            <Routes>
                <Route path="/" element={<Menu/>} />
                <Route path="book" element={<Books/>}/>
                <Route path="author" element={<Authors/>}/>
                <Route path="genre" element={<Genres/>}/>
                <Route path="book/:id" element={<HOCBook/>}/>
                <Route path="book/add" element={<HOCNewBook/>}/>
                <Route path="book/:id/comment" element={<HOCComments/>}/>
            </Routes>
        )
    }
};