using Microsoft.Identity.Client;

namespace E_commerce.Service
{
    public class CustomAuthenticationResult
    {
        public AuthenticationResult OriginalResult { get; }
        public string GeneratedToken { get; }

        public CustomAuthenticationResult(AuthenticationResult originalResult, string generatedToken)
        {
            OriginalResult = originalResult;
            GeneratedToken = generatedToken;
        }
    }
}
