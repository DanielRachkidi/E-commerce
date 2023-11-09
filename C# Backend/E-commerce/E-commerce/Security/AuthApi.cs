using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Authentication;
using System.Threading.Tasks;
using E_commerce.Data;
using E_commerce.DTO;
using E_commerce.Service;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;

namespace E_commerce.Security
{
    [ApiController]
    [Route("api/user")]
    [AllowAnonymous]
    public class AuthApi : ControllerBase
    {
        private readonly CustomAuthenticationProvider _provider;
        private readonly JwtSecurityTokenHandler _jwtHandler;
        private IUserService userService;

        public AuthApi(CustomAuthenticationProvider provider, JwtSecurityTokenHandler jwtHandler, YourDbContext yourDbContext)
        {
            _provider = provider;
            _jwtHandler = jwtHandler;
            userService = new YourUserServiceImplementation(dbContext: yourDbContext);
        }

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] AuthRequest request)
        {
            try
            {
                // Authenticate the user using the custom authentication provider
                var authenticationResult = await _provider.AuthenticateAsync(new UsernamePasswordAuthenticationRequest
                {
                    Username = request.Username,
                    Password = request.Password
                });

                // Authentication successful, return a success message.
                if (authenticationResult == null)
                {
                    return Ok(User.Identity);
                }

                throw new AuthenticationException("Invalid credentials");
            }
            catch (AuthenticationException ex)
            {
                return Unauthorized(new { Message = ex.Message });
            }
            catch (Exception)
            {
                return StatusCode(500, new { Message = "An error occurred during login." });
            }
        }
    }
}
