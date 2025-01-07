import '../styles/Login.css';
import CountdownTimer from "./CountdownTimer.jsx";

const Login = () => {

  const handleGoogleLogin = () => {
    window.location.href = "/oauth2/authorization/google"
  }

  return (
    <div className={'flex justify-center h-dvh items-center'}>
      <div className={'border border-white rounded-[8px] p-12 flex flex-col justify-center items-center gap-7 backdrop-blur'}>
        <h1 className={'text-4xl'}>Login</h1>
        <button
          className={'border-green-600 bg-green-800 border rounded-[4px] p-[5px] hover:bg-green-600 transition duration-200'}
          onClick={handleGoogleLogin}>
          Sign in with Google
        </button>
        <CountdownTimer page={'Login'}/>
      </div>
    </div>
  )
}

export default Login