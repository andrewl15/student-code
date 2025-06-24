import { Routes, Route, Navigate } from "react-router-dom";
import ProtectedRoute from "../ProtectedRoute";
import LoginView from "../../views/LoginView/LoginView";
import LogoutView from "../../views/LogoutView";
import QuoteView from "../../views/QuoteView/QuoteView";
import QuoteDetailsView from "../../views/QuoteDetailsView/QuoteDetailsView";
import AddQuoteView from "../../views/AddQuoteView/AddQuoteView";

export default function MainContent({ onLogin, onLogout }) {
  return (
    <main>
      <Routes>
        <Route path="/" element={<QuoteView />} />
        <Route path="/quotes/:quoteId" element={<QuoteDetailsView />} />
        <Route path="/login" element={<LoginView onLogin={onLogin} />} />
        <Route path="/logout" element={<LogoutView onLogout={onLogout} />} />
        <Route
          path="/addQuote"
          element={
            <ProtectedRoute>
              <AddQuoteView />
            </ProtectedRoute>
          }
        />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </main>
  );
}
