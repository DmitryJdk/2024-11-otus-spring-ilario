import React, {useEffect, useState} from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";

const Genres = () => {

    const [genres, setGenres] = useState([]);

    useEffect(() => {
        async function fetchData() {
            (await fetch('/api/genre')).json()
                .then(res => setGenres(res));
        }

        fetchData().then();

    }, []);

    return (
        <div>
            <Header title={'Genres'}/>
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        genres.map((genre, i) => (
                            <tr key={i}>
                                <td>{genre.id}</td>
                                <td>{genre.name}</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
            <NavLink to={"/"}>Back</NavLink>
        </div>
    )
}

export default Genres;