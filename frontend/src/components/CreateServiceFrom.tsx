import { useState } from "react";

interface CreateServiceFromProps {
  onSubmit: (name: string, url: string) => void;
}

function CreateServiceFrom({ onSubmit }: CreateServiceFromProps){
    const [name, setName] = useState("");
    const [url, setUrl] = useState("");

    const handleSubmit = (e: React.SubmitEvent) => { 
        e.preventDefault(); 
        onSubmit(name, url); 
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <input
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Name"
            required
            className="rounded border p-2"
        />

        <input
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            placeholder="Url"
            required
            type="url"
            className="rounded border p-2"
        />

        <button
            type="submit"
            className="rounded bg-blue-500 p-2 text-white hover:bg-blue-600"
        >
            Submit
        </button>
        </form>
    );
}

export default CreateServiceFrom;