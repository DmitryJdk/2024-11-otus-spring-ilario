import ndjsonStream from "can-ndjson-stream";

export async function fetchNdjson (state, url, field) {
    const response = await fetch(url);
    const ndjson = ndjsonStream(response.body);
    const reader = ndjson.getReader();
    let result = [];
    while (true) {
        const {done, value} = await reader.read();
        if (done) {
            break;
        }
        result = [value, ...result];
        switch (field) {
            case 'authors':
                state.setState({authors: result});
                break;
            case 'books':
                state.setState({books: result});
                break;
            case 'book':
                state.setState({book: value});
                break;
            case 'genres':
                state.setState({genres: result});
                break;
            case 'comments':
                state.setState({comments: result});
                break;
            default:
                break;
        }
        console.log(value);
    }
    reader.releaseLock();
    return result
}