using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;
using System;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Microsoft.Extensions.Diagnostics.HealthChecks;
using E_commerce.Data;
using System.Threading;
using E_commerce.Health;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.EntityFrameworkCore;

namespace E_commerce
{
    public class Starts
    {
        public Starts(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllers()
                .AddJsonOptions(options =>
                {
                    options.JsonSerializerOptions.Converters.Add(new JsonStringEnumConverter());
                });

            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = "E-commerce API", Version = "v1" });
            });
            services.AddSwaggerGenNewtonsoftSupport();

            services.AddDbContext<YourDbContext>(options =>
                options.UseNpgsql(Configuration.GetConnectionString("MyConnectionString")));


            services.AddHealthChecks()
                .AddCheck<DbContextHealthCheck>("DbContextHealthCheck", failureStatus: HealthStatus.Degraded, tags: new[] { "db" });

            services.AddHostedService<HeartbeatBackgroundService>();



            services.TryAddSingleton<IHealthCheckService, YourHealthCheckServiceImplementation>();

        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(c =>
                {
                    c.SwaggerEndpoint("/swagger/v1/swagger.json", "E-commerce API v1");
                });
            }
            else
            {
                app.UseExceptionHandler("/error");
                app.UseHsts();
            }

            app.UseRouting();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
                endpoints.MapHealthChecks("/health");
            });
        }
    }



    public interface IHealthCheckService
    {
        Task<HealthCheckResult> CheckHealthAsync(CancellationToken cancellationToken = default);
    }
    public class HeartbeatBackgroundService : IHostedService, IDisposable
    {
        private readonly IServiceScopeFactory _serviceScopeFactory;
        private readonly TimeSpan _interval = TimeSpan.FromSeconds(5);
        private Timer _timer;

        public HeartbeatBackgroundService(IServiceScopeFactory serviceScopeFactory)
        {
            _serviceScopeFactory = serviceScopeFactory;
        }

        public Task StartAsync(CancellationToken cancellationToken)
        {
            _timer = new Timer(ExecuteHealthCheck, null, TimeSpan.Zero, _interval);
            return Task.CompletedTask;
        }

        public Task StopAsync(CancellationToken cancellationToken)
        {
            _timer?.Change(Timeout.Infinite, 0);
            return Task.CompletedTask;
        }

        private async void ExecuteHealthCheck(object state)
        {
            using (var scope = _serviceScopeFactory.CreateScope())
            {
                var healthCheckService = scope.ServiceProvider.GetRequiredService<IHealthCheckService>();
                var healthCheckResult = await healthCheckService.CheckHealthAsync();

                if (healthCheckResult.Status == HealthStatus.Healthy)
                {
                    Console.WriteLine("Application is healthy");
                }
                else
                {
                    Console.WriteLine("Application is unhealthy");
                }
            }
        }


        public void Dispose()
        {
            _timer?.Dispose();
        }
    }
}