import { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

// 🌟 HIGH-QUALITY FLAT ICONS MAP (Custom Raw SVG Format for Smooth Styling)
const PageIcons = {
  LENGTH: (
    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
      <path d="m2 12 5-5 5 5-5 5Z"/><path d="m12 12 5-5 5 5-5 5Z"/><path d="M7 12h10"/>
    </svg>
  ),
  WEIGHT: (
    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
      <circle cx="12" cy="5" r="3"/><path d="M12 8v13"/><path d="m19 14-7 7-7-7"/>
    </svg>
  ),
  VOLUME: (
    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
      <path d="M12 2v20"/><path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
    </svg>
  ),
  TEMPERATURE: (
    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
      <path d="M14 4v10.54a4 4 0 1 1-4 0V4a2 2 0 0 1 4 0Z"/>
    </svg>
  )
};

function App() {
  // ---------------- AUTH STATE ----------------
  const [token, setToken] = useState(localStorage.getItem("token") || "");
  const [userProfile, setUserProfile] = useState(null);

  // --- POPUP STATE ---
  const [showAuthPopup, setShowAuthPopup] = useState(false);

  // ---------------- MULTIPAGE VIRTUAL STATE ----------------
  const [type, setType] = useState(() => {
    const hash = window.location.hash.toUpperCase().replace("#", "");
    return ["LENGTH", "WEIGHT", "VOLUME", "TEMPERATURE"].includes(hash) ? hash : "LENGTH";
  });

  // ---------------- VALUES ----------------
  const [firstValue, setFirstValue] = useState("");
  const [secondValue, setSecondValue] = useState("");

  const unitsMap = {
    LENGTH: ["FEET", "INCH", "YARDS", "CENTIMETERS"],
    WEIGHT: ["KILOGRAM", "GRAM", "POUND"],
    VOLUME: ["LITRE", "MILLILITRE", "GALLON"],
    TEMPERATURE: ["CELSIUS", "FAHRENHEIT", "KELVIN"],
  };

  const [firstUnit, setFirstUnit] = useState(unitsMap[type][0]);
  const [secondUnit, setSecondUnit] = useState(unitsMap[type][0]);
  const [targetUnit, setTargetUnit] = useState(unitsMap[type][0]);

  const [operation, setOperation] = useState("convert");
  const [result, setResult] = useState("");

  const API = "http://localhost:8080/api/v1/quantities";
  const AUTH_API = "http://localhost:8080/api/auth";

  // ---------------- SYNC STATE WITH BROWSER ROUTING SYSTEM ----------------
  useEffect(() => {
    const handleHashChange = () => {
      const hash = window.location.hash.toUpperCase().replace("#", "");
      if (["LENGTH", "WEIGHT", "VOLUME", "TEMPERATURE"].includes(hash)) {
        setType(hash);
        setFirstUnit(unitsMap[hash][0]);
        setSecondUnit(unitsMap[hash][0]);
        setTargetUnit(unitsMap[hash][0]);
        setResult("");
      }
    };
    window.addEventListener("hashchange", handleHashChange);
    return () => window.removeEventListener("hashchange", handleHashChange);
  }, []);

  // ---------------- CHECK FOR TOKEN IN URL PARAMETERS ----------------
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const tokenFromUrl = urlParams.get("token");

    if (tokenFromUrl) {
      localStorage.setItem("token", tokenFromUrl);
      setToken(tokenFromUrl);
      window.history.replaceState({}, document.title, window.location.pathname + window.location.hash);
    }
  }, []);

  // ---------------- FETCH USER PROFILE (IF TOKEN EXISTS) ----------------
  useEffect(() => {
    if (token) {
      axios
        .get(`${AUTH_API}/profile`, {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((res) => {
          setUserProfile(res.data);
        })
        .catch((err) => {
          console.error("Profile fetch failed:", err);
          handleLogout();
        });
    }
  }, [token]);

  // ---------------- LOGIN / LOGOUT HANDLERS ----------------
  const handleGoogleLogin = () => {
    window.location.href = `${AUTH_API}/login`;
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken("");
    setUserProfile(null);
    setResult("");
  };

  const units = unitsMap[type];

  const changeType = (newType) => {
    window.location.hash = newType.toLowerCase();
    setType(newType);
    setFirstUnit(unitsMap[newType][0]);
    setSecondUnit(unitsMap[newType][0]);
    setTargetUnit(unitsMap[newType][0]);
    setResult("");
  };

  // ---------------- HANDLE CALC ----------------
  const handleCalculate = async () => {
    if (!token) {
      setShowAuthPopup(true);
      return;
    }

    if (!firstValue) {
      setResult("Enter value");
      return;
    }

    if (operation !== "convert" && operation !== "compare" && !secondValue) {
      setResult("Enter second value");
      return;
    }

    const axiosConfig = {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    };

    try {
      if (operation === "convert") {
        const request = {
          value: Number(firstValue),
          unit: firstUnit,
          measurementType: type,
        };

        const response = await axios.post(
          `${API}/convert?targetUnit=${targetUnit}`,
          request,
          axiosConfig
        );

        setResult(`${response.data.value} ${response.data.unit}`);
        return;
      }

      const request = {
        first: {
          value: Number(firstValue),
          unit: firstUnit,
          measurementType: type,
        },
        second: {
          value: Number(secondValue),
          unit: secondUnit,
          measurementType: type,
        },
        targetUnit: targetUnit,
      };

      const response = await axios.post(
        `${API}/${operation}`,
        request,
        axiosConfig
      );

      setResult(`${response.data.value} ${response.data.unit}`);
    } catch (error) {
      if (error.response?.status === 401) {
        setResult("Error: Unauthorized! Token valid nahi hai ya expire ho gaya.");
        handleLogout();
      } else {
        setResult("Error: " + (error.response?.data?.message || error.message));
      }
    }
  };

  return (
    <div className="page">
      {/* ⚡ LOGO LEFT ALIGNED HEADER TOPBAR */}
      <div className="topbar">
        <div className="logo-section">
          <div className="logo-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
              <path d="M21.3 15.3a2.82 2.82 0 1 0-4-4l-4.3 4.3a2.82 2.82 0 1 0 4 4Z"/>
              <path d="M12 12V3h9"/>
              <path d="M16 6h3"/>
              <path d="M16 9h2"/>
              <path d="M3 9h9v12H3Z"/>
              <path d="M6 12h3"/>
              <path d="M6 15h2"/>
              <path d="M6 18h4"/>
            </svg>
          </div>
          <div className="logo-text">
            <h1>Quantify Pro</h1>
            <p>Quantity Measurement Dashboard</p>
          </div>
        </div>

        {/* Dynamic Login / Profile UI Section */}
        <div className="auth-section">
          {token && userProfile ? (
            <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
              <img 
                src={userProfile.picture || "https://via.placeholder.com/40"} 
                alt="Profile" 
                style={{ borderRadius: "50%", width: "40px", height: "40px" }}
              />
              <span style={{ color: "white", fontWeight: "bold" }}>Hi, {userProfile.name}</span>
              <button onClick={handleLogout} style={{ padding: "5px 10px", cursor: "pointer", background: "#ff4d4d", color: "white", border: "none", borderRadius: "4px" }}>
                Logout
              </button>
            </div>
          ) : (
            <button 
              onClick={handleGoogleLogin} 
              style={{ padding: "10px 15px", background: "#4285F4", color: "white", border: "none", borderRadius: "5px", fontWeight: "bold", cursor: "pointer" }}
            >
              Sign in with Google
            </button>
          )}
        </div>
      </div>

      <div className="layout">
        {/* LEFT PANEL */}
        <div className="panel">
          <div className="page-header-title">
            {PageIcons[type]}
            <h2>{type}</h2>
          </div>
          
          <h2>Select Operation</h2>
          <div className="ops">
            <button onClick={() => setOperation("add")} className={operation === "add" ? "active" : ""}>ADD</button>
            <button onClick={() => setOperation("subtract")} className={operation === "subtract" ? "active" : ""}>SUB</button>
            <button onClick={() => setOperation("divide")} className={operation === "divide" ? "active" : ""}>DIV</button>
            <button onClick={() => setOperation("convert")} className={operation === "convert" ? "active" : ""}>CONVERT</button>
            <button onClick={() => setOperation("compare")} className={operation === "compare" ? "active" : ""}>COMPARE</button>
          </div>

          <h2>Input Values ({type})</h2>

          {/* FIRST INPUT */}
          <div className="inputRow">
            <input
              type="number"
              value={firstValue}
              onChange={(e) => setFirstValue(e.target.value)}
              placeholder="First Value"
            />
            <select value={firstUnit} onChange={(e) => setFirstUnit(e.target.value)}>
              {units.map((u) => <option key={u}>{u}</option>)}
            </select>
          </div>

          {/* SECOND INPUT */}
          {operation !== "convert" && (
            <div className="inputRow">
              <input
                type="number"
                value={secondValue}
                onChange={(e) => setSecondValue(e.target.value)}
                placeholder="Second Value"
              />
              <select value={secondUnit} onChange={(e) => setSecondUnit(e.target.value)}>
                {units.map((u) => <option key={u}>{u}</option>)}
              </select>
            </div>
          )}

          {/* CONVERT PANEL */}
          {operation === "convert" && (
            <div className="convertPanel">
              <h3>Convert To</h3>
              <div className="unitGrid">
                {units.map((u) => (
                  <button
                    key={u}
                    className={targetUnit === u ? "unit active" : "unit"}
                    onClick={() => setTargetUnit(u)}
                  >
                    {u}
                  </button>
                ))}
              </div>
            </div>
          )}

          <button className="calcBtn" onClick={handleCalculate}>
            EXECUTE {operation.toUpperCase()}
          </button>
        </div>

        {/* RIGHT PANEL */}
        <div className="resultPanel">
          <h2>Result Display Matrix</h2>
          <div className="resultBox">{result || "No Result Yet"}</div>
        </div>
      </div>

      {/* BOTTOM NAVIGATION WITH ROUTING LINKS AND ICONS */}
      <div className="bottomNav">
        <button onClick={() => changeType("LENGTH")} className={type === "LENGTH" ? "active" : ""}>
          {PageIcons.LENGTH} <span>Length</span>
        </button>
        <button onClick={() => changeType("WEIGHT")} className={type === "WEIGHT" ? "active" : ""}>
          {PageIcons.WEIGHT} <span>Weight</span>
        </button>
        <button onClick={() => changeType("VOLUME")} className={type === "VOLUME" ? "active" : ""}>
          {PageIcons.VOLUME} <span>Volume</span>
        </button>
        <button onClick={() => changeType("TEMPERATURE")} className={type === "TEMPERATURE" ? "active" : ""}>
          {PageIcons.TEMPERATURE} <span>Temperature</span>
        </button>
      </div>

      {/* AUTHENTICATION POPUP DIALOG */}
      {showAuthPopup && (
        <div className="popup-overlay">
          <div className="popup-box">
            <div className="popup-icon">🔒</div>
            <h2>Authentication Required</h2>
            <p>Please sign in with Google to access calculations and conversions.</p>
            <div className="popup-buttons">
              <button className="popup-login-btn" onClick={() => { setShowAuthPopup(false); handleGoogleLogin(); }}>
                Sign In Now
              </button>
              <button className="popup-close-btn" onClick={() => setShowAuthPopup(false)}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;