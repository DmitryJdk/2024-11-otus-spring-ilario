import React from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";
import {fetchNdjson} from "../api/ApiFunctions";

class Authors extends React.Component {

    constructor() {
        super();
        this.state = {authors: []};
    }

    componentDidMount() {
        fetchNdjson(this, "/author", 'authors')
            .then(r => {
                console.log(r)
            });
    }

    render() {
        return (
            <div>
                <Header title={'Authors'}/>
                <div>
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Full Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.authors.map((author, i) => (
                                <tr key={i}>
                                    <td>{author.id}</td>
                                    <td>{author.fullName}</td>
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

export default Authors;