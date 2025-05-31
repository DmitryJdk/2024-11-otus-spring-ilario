import ndjsonStream from "can-ndjson-stream";

export async function fetchNdjson(url, token) {
    const response = await fetch(url, {
            headers: {'Authorization': `Bearer ${token}`}
        }
    );
    const ndjson = ndjsonStream(response.body);
    const reader = ndjson.getReader();
    let result = {};
    while (true) {
        const {done, value} = await reader.read();
        if (done) {
            break;
        }
        result = value;
        console.log(value);
    }
    reader.releaseLock();
    return result
}

export async function fetchNdjsonWithState(url, onData, token, abortSignal) {
    const response = await fetch(url, {
            headers: {'Authorization': `Bearer ${token}`},
            signal: abortSignal
        }
    );
    const ndjson = ndjsonStream(response.body);
    const reader = ndjson.getReader();
    try {
        while (true) {
            const {done, value} = await reader.read();
            if (done) break;
            onData(value);
        }
    } catch (error) {
        if (error.name !== 'AbortError') {
            console.error('Stream error:', error);
        } else {
            console.log('NDJSON stream aborted');
        }
    } finally {
        reader.releaseLock();
    }
}