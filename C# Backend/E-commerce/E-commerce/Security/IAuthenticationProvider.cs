using Microsoft.Identity.Client;
using System.Threading.Tasks;

namespace E_commerce.Security
{
    public interface IAuthenticationProvider
    {
        Task<AuthenticationResult> AuthenticateAsync(AuthenticationRequest request);
        bool CanHandle(AuthenticationRequest request);
    }
}
