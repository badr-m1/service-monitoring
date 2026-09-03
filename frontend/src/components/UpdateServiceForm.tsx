import { useState } from "react";

interface UpdateServiceFormProps {
  onSubmit: (name: string) => void;
}

function UpdateServiceForm({ onSubmit }: UpdateServiceFormProps){
    const [name, setName] = useState("");

    const handleSubmit = (e: React.SubmitEvent) => { 
        e.preventDefault(); 
        onSubmit(name); 
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

        <button
            type="submit"
            className="rounded bg-blue-500 p-2 text-white hover:bg-blue-600"
        >
            Submit
        </button>
        </form>
    );
}

export default UpdateServiceForm;