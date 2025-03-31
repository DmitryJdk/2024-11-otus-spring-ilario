import React from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";

class Genres extends React.Component {

    constructor() {
        super();
        this.state = {genres: []};
    }

    componentDidMount() {
        fetch('/api/genre')
            .then(response => response.json())
            .then(genres => this.setState({genres}));
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