import React from 'react';
import {NavLink} from "react-router-dom";
import Header from "./Header";

const Menu = () => (
    <div>
        <Header title={'Menu'}/>
        <NavLink to={'/author'}>Authors</NavLink>
        <NavLink to={'/genre'}>Genres</NavLink>
        <NavLink to={'/book'}>Books</NavLink>
    </div>
);

export default Menu;