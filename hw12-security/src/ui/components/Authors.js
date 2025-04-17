import React, {useEffect, useState} from 'react';
import Header from "./Header";
import {NavLink} from "react-router-dom";

const Authors = () => {
    const [authors, setAuthors] = useState([]);

    useEffect(() => {
        async function fetchData(){
            (await fetch('/api/author')).json()
                .then(res => setAuthors(res));
        }
        fetchData().then();
    }, []);

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
                        authors.map((author, i) => (
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

export default Authors;