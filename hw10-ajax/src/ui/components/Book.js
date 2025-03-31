import React from 'react';
import Header from "./Header";
import {NavLink, useNavigate, useParams} from "react-router-dom";

function withRouter(Component) {
    function ComponentWithRouter(props) {
        let params = useParams()
        let navigate = useNavigate();
        return <Component {...props} params={params} navigate={navigate}/>
    }

    return ComponentWithRouter
}

class Book extends React.Component {

    constructor() {
        super();
        this.state = {book: {id: '', title: '', author: '', genres: []}, authors: [], genres: []};
    }

    componentDidMount() {
        fetch('/api/book/' + this.props.params.id)
            .then(response => response.json())
            .then(book => this.setState({book}));
        fetch('/api/author')
            .then(response => response.json())
            .then(authors => this.setState({authors}));
        fetch('/api/genre')
            .then(response => response.json())
            .then(genres => this.setState({genres}));
    }

    render() {
        return (
            <div>
                <Header title={'Book'}/>
                {
                    <div>
                        <div className="row">
                            <label htmlFor="id-input">ID:</label>
                            <input id="id-input" type="text" readOnly="readonly" value={`${this.state.book.id}`}/>
                        </div>

                        <div className="row">
                            <label htmlFor="title-input">Title:</label>
                            <input id="title-input" name="title" type="text" value={`${this.state.book.title}`}
                                   onChange={e => this.onTodoChange(e)}/>
                        </div>

                        <div className="row">
                            <label htmlFor="author-input">Author:</label>
                            <select id="author-input" name="authorId">
                                <option value="0">select author</option>
                                {
                                    this.state.authors.map((author) => (
                                        <option value={`${author.id}`}
                                                selected={this.state.book.author === author.fullName}
                                        >{author.fullName}</option>))
                                }
                            </select>
                        </div>

                        <div className="row">
                            <label htmlFor="genres-input">Genres:</label>
                            <select id="genres-input" multiple="multiple" name="genresIds">
                                {
                                    this.state.genres.map((genre) => (
                                        <option value={`${genre.id}`}
                                                selected={this.state.book.genres.indexOf(genre.name) >= 0}
                                        >{genre.name}</option>))
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

    async saveBook(id) {
        let titleValue = document.getElementById("title-input").value
        let authorValue = document.getElementById("author-input").value
        let genresSelected = document.getElementById("genres-input").selectedOptions
        let genreValues = Array.from(genresSelected).map(({value}) => value);
        console.log("editing book");
        let response = await fetch('/api/book/' + id, {
            method: 'PUT',
            redirect: 'follow',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({title: titleValue, authorId: authorValue, genresIds: genreValues})
        }).then(response => response.json());
        console.log("book edited" + response);
        this.props.navigate('/book');
        console.log("navigating to /book");
    }
}

const HOCBook = withRouter(Book);

export default HOCBook;