# Product & Hospital Search

## Project Description
Our project is based on both **Product** and **Hospital** domains.  
Although they are independent of each other, the **intention of solving the problem is the same**.




## Team

- **Team Name:** IM GPT  
- **Members:**
  - Paras Lehana  
  - Abhinav Kaushik  
  - Khem Chand  
  - Amit Kumar  



## Hackathon Theme / Challenge
- **Theme:** Theme 2
- **Challenge:**
Example user query:  
> “Want to buy red or orange LED from Syska or better brands, under 10 wattage.”

We understand:
- The query refers to an **LED Bulb**, not a TV.  
- **9-watt** will work, but **blue color** should be excluded.  
- **Syska** is preferred.  

This type of intelligent contextual understanding is **not possible with leading e-commerce platforms** at the moment.  
That’s why we are **hacking the impossible**!




## What We Built & How to Run It

### Steps to Run
1. **Run N8N Workflow tool** using Docker Compose:  
   ```bash
   docker-compose -f N8N/docker-compose.yml up -d

2. Run Elasticsearch & Kibana from official DockerHub images.
3. Run Elastic MCP Server using: 
   docker-compose -f ElasticMPCServer/docker-compose.yml up -d
4. Import N8N workflow:

  Import the file: N8N/My workflow.json into N8N.

    The workflow uses an AI Agent with:

    2 Tools and 1 Model via OpenAI Chat Model
    a) Claude API model (set your API key)
    b) Elastic MCP Server – interacts with the LLM to reason and fetch data from Elasticsearch (set encoded authorization key for Elasticsearch DB authentication)
    c) HTTP endpoint for ES – since Elastic MCP Server doesn’t natively support retrieving nested index field mappings, we defined a custom exception in the prompt to call this tool (set username/password for Elasticsearch DB authentication).



### Tech Stack

  Elasticsearch
  Kibana
  ElasticSearch MCP Server
  LLM Model – Claude
  N8N Workflow Orchestration
  Descope
  Cequence



### Demo Video

   👉 Link coming soon...
