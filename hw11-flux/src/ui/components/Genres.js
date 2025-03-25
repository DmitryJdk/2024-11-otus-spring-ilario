import React from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";
import {fetchNdjson} from "../api/ApiFunctions";

class Genres extends React.Component {

    constructor() {
        super();
        this.state = {genres: []};
    }

    componentDidMount() {
        fetchNdjson(this, "/genre", 'genres').then(r => {
            console.log(r)
        });
    }

    render() {
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
                            this.state.genres.map((genre, i) => (
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
}

export default Genres;