interface PopupProps {
  title: string;
  children: React.ReactNode;
  onClose: () => void;
}

export default function Popup({ title, children, onClose }: PopupProps) {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black/50">
      <div className="w-96 rounded-lg bg-white p-6 shadow-lg">
        <div className="mb-4 flex items-center justify-between">
          <h2 className="text-xl font-semibold">{title}</h2>

          <button onClick={onClose} className="text-gray-500">
            ✕
          </button>
        </div>

        {children}
      </div>
    </div>
  );
}