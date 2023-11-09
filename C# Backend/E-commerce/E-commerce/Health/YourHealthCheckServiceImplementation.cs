using Microsoft.Extensions.Diagnostics.HealthChecks;

namespace E_commerce.Health
{
    public class YourHealthCheckServiceImplementation : IHealthCheckService
    {
        public Task<HealthCheckResult> CheckHealthAsync(CancellationToken cancellationToken = default)
        {
            // Implement the health check logic here
            // Return a HealthCheckResult indicating the health status

            // Example: Always return a healthy status
            return Task.FromResult(HealthCheckResult.Healthy());
        }
    }

}
