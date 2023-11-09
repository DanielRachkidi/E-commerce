namespace E_commerce.Security
{
    public class UsernamePasswordAuthenticationRequest : AuthenticationRequest
    {
        public string Username { get; set; }
        public string Password { get; set; }
    }
}
