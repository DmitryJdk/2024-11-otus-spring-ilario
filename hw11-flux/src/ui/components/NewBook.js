import React from 'react';
import Header from "./Header";
import {NavLink, useNavigate} from "react-router-dom";
import {fetchNdjson} from "../api/ApiFunctions";

function withRouter(Component) {
    function ComponentWithRouter(props) {
        let navigate = useNavigate();
        return <Component {...props} navigate={navigate}/>
    }
    return ComponentWithRouter
}

class NewBook extends React.Component {

    constructor() {
        super();
        this.state = {book: {id: '', title: '', author: '', genres: []}, authors: [], genres: []};
    }

    componentDidMount() {
        fetchNdjson(this, "/api/author", 'authors')
            .then(r => {
                console.log(r)
            });
        fetchNdjson(this, "/api/genre", 'genres')
            .then(r => {
                console.log(r)
            });
    }

    render() {
        return (
            <div>
                <Header title={'Book'}/>
                {
                    <div>
                        <div className="row">
                            <label htmlFor="title-input">Title:</label>
                            <input id="title-input" name="title" type="text"
                                   onChange={e => this.onTodoChange(e)}/>
                        </div>
                        <div className="row">
                            <label htmlFor="author-input">Author:</label>
                            <select id="author-input" name="authorId">
                                <option value="0">select author</option>
                                {
                                    this.state.authors.map((author) => (
                                        <option value={`${author.id}`}>{author.fullName}</option>))
                                }
                            </select>
                        </div>
                        <div className="row">
                            <label htmlFor="genres-input">Genres:</label>
                            <select id="genres-input" multiple="multiple" name="genresIds">
                                {
                                    this.state.genres.map((genre) => (
                                        <option value={`${genre.id}`}>{genre.name}</option>))
                                }
                            </select>
                        </div>
                        <a onClick={()=>this.saveBook(this.state.book.id)}>Save</a>
                    </div>
                }
                <NavLink to={"/book"}>Back</NavLink>
            </div>
        )
    }

    onTodoChange(e){
        const { book } = { ...this.state };
        const currentState = book;
        const { name, value } = e.target;
        currentState[name] = value;
        this.setState({ book: currentState });
    }

    async saveBook() {
        let titleValue = document.getElementById("title-input").value
        let authorValue = document.getElementById("author-input").value
        let genresSelected = document.getElementById("genres-input").selectedOptions
        let genreValues = Array.from(genresSelected).map(({value}) => value);
        console.log("creating book");
        let response = await fetch('/api/book', {
            method: 'POST',
            redirect: 'follow',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({title: titleValue, authorId: authorValue, genresIds: genreValues})
        }).then(response => response.json());
        console.log("book created" + response);
        this.props.navigate('/book');
        console.log("navigating to /book");
    }
}

const HOCNewBook = withRouter(NewBook);

export default HOCNewBook;