import React, {useEffect, useState} from 'react';
import Header from "./Header";
import withRouter from "../function/ComponentWithRouter";
import {NavLink} from "react-router-dom";

const NewComment = (props) => {

    const [bookId, setBookId] = useState('');
    const [comment, setComment] = useState({text: ''});

    useEffect(() => {
        setBookId(props.params.bookId);
    }, []);

    const saveComment = async () => {
        let textValue = document.getElementById("text-input").value
        await fetch('/api/book/' + bookId + '/comment', {
            method: 'POST',
            redirect: 'follow',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({text: textValue})
        }).then(response => response.json());
        props.navigate('/book/' + bookId + '/comment');
    }

    return (
        <div>
            <Header title={'Comment'}/>
            {
                <div>
                    <div className="row">
                        <label htmlFor="text-input">Text:</label>
                        <input id="text-input" name="text" type="text"
                               onChange={e => setComment({
                                   text: e.target.value
                               })}/>
                    </div>
                    <a onClick={() => saveComment()}>Save</a>
                </div>
            }
            <NavLink to={"/book"}>Back</NavLink>
        </div>
    )
}

const HOCNewComment = withRouter(NewComment);

export default HOCNewComment;