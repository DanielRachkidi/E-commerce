using System;
using System.Security.Authentication;
using System.Threading.Tasks;
using E_commerce.DataModel;
using E_commerce.Service;
using Microsoft.Identity.Client;

namespace E_commerce.Security
{
    public class CustomAuthenticationProvider : IAuthenticationProvider
    {
        private readonly IUserService userService;

        public CustomAuthenticationProvider(IUserService userService)
        {
            this.userService = userService;
        }

        public async Task<AuthenticationResult> AuthenticateAsync(AuthenticationRequest request)
        {
            if (!(request is UsernamePasswordAuthenticationRequest usernamePasswordRequest))
            {
                throw new AuthenticationException("Invalid authentication request");
            }

            string username = usernamePasswordRequest.Username;
            string password = usernamePasswordRequest.Password;

            // Find the user in the database based on the provided username and hashed password
            var hashedPassword = User.HashPassword(password);
            var user = await userService.FindByCredentialsAsync(username, hashedPassword);

            if (user != null)
            {
                // Authentication successful, return the user directly
                return null;
            }

            throw new AuthenticationException("Invalid credentials");
        }

        public bool CanHandle(AuthenticationRequest request)
        {
            return request is UsernamePasswordAuthenticationRequest;
        }
    }
}
